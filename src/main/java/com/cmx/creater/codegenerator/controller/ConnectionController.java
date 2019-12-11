package com.cmx.creater.codegenerator.controller;


import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.common.HttpStatus;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.parser.CreateTableParser;
import com.cmx.creater.codegenerator.service.GeneratorService;
import com.cmx.creater.codegenerator.vo.TableVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmx
 */
@Slf4j
@RestController
@RequestMapping("/connect")
public class ConnectionController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private SessionTableStore sessionTableStore;

    @Autowired
    private CreateTableParser createTableParser;

    @RequestMapping(value = "/getTables", method = RequestMethod.GET)
    public ApiResult<List<TableVO>> getTables(@RequestParam String host, @RequestParam String userName,
                                              @RequestParam String password, Integer port,
                                              @RequestParam String database, HttpServletRequest request){
        log.info("get connect request : {},{},{},{}", host , userName, port, database);
        ApiResult<List<TableVO>> result = new ApiResult<>();

        ConnectionModel model = new ConnectionModel();
        model.setHost(host);
        model.setDatabase(database);
        model.setUserName(userName);
        model.setPassword(password);
        model.setPort(port == null ? 3306 : port);
        try {
            List<Table> tables = generatorService.getTables(model, null);
            return result.successResult(cacheTableInfo(request, tables));
        }catch (GeneratorException e){
            log.error("get table info get error :", e);
            return result.failResult(e);
        }

    }

    @RequestMapping(value = "parseSqlFile", method = RequestMethod.POST)
    public ApiResult<List<TableVO>> getTablesFromFile(MultipartFile file, HttpServletRequest request) {
        log.info("parse table file request : {}", file.getSize());
        ApiResult<List<TableVO>> result = new ApiResult<>();
        try{
            InputStreamReader ir = new InputStreamReader(file.getInputStream());
            List<Table> tables = createTableParser.parseSqlToTable(ir);
            return result.successResult(cacheTableInfo(request, tables));
        } catch (Exception e) {
            log.error("parse sql file error : ", e);
            return result.failResult("parse error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<TableVO> cacheTableInfo(HttpServletRequest request, List<Table> tables) {
        sessionTableStore.setSession(request.getSession());
        sessionTableStore.setCacheTables(tables);
        List<TableVO> tbvs = new ArrayList<>();
        for(Table t : tables) {
            TableVO tableVO = new TableVO();
            BeanUtils.copyProperties(t, tableVO);
            tbvs.add(tableVO);
        }
        return tbvs;
    }

}
