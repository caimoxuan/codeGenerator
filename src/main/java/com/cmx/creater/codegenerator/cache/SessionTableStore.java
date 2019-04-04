package com.cmx.creater.codegenerator.cache;

import com.cmx.creater.codegenerator.common.GeneratorConfig;
import com.cmx.creater.codegenerator.common.Table;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

/**
 * @author cmx
 * @Date 2019/4/1 22:32
 */

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionTableStore {

    private List<Table> cacheTables;

    private Connection cacheConnection;

    private HttpSession session;

    private GeneratorConfig cacheConfig;

    SessionTableStore(){
        this.cacheConfig = new GeneratorConfig();
    }

}
