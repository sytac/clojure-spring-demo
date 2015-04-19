package io.sytac.training.api;

import clojure.lang.RT;
import clojure.lang.Symbol;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class NRepl {

    private final AtomicInteger port = new AtomicInteger(-1);

    @RequestMapping(value = "/api/nrepl", method = RequestMethod.POST)
    public NReplDetails start(){
        Integer port = this.port.get();
        if(port > 0) {
            return new NReplDetails(port, "started");
        }

        Map server = (Map)RT.var("clojure.tools.nrepl.server", "start-server").applyTo(RT.list());
        port = (Integer)server.get(RT.keyword(null, "port"));

        return new NReplDetails(port, "started");
    }

    @RequestMapping(value = "/api/nrepl/stop")
    public NReplDetails stop(){
        Integer port = this.port.get();
        if(port > 0) {
            RT.var("clojure.tools.nrepl.server", "stop-server").applyTo(RT.list());
            return new NReplDetails(null, "stopped");
        }

        return new NReplDetails(null, "stopped");
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    private class NReplDetails {

        private final String port;

        private final String status;
        private NReplDetails(Integer port, String status) {
            this.port = port != null? port.toString() : "";
            this.status = status;
        }

        public String getPort(){
            return this.port;
        }

    }

    @PostConstruct
    private void bootClojure() {
        Symbol ns = Symbol.intern("clojure.tools.nrepl.server");
        RT.var("clojure.core", "require").invoke(ns);
    }
}
