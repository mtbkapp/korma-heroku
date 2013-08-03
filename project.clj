(defproject places "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.postgresql/postgresql "9.2-1003-jdbc4"]
                 [korma "0.3.0-RC5"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail 
                                              javax.jms/jms 
                                              com.sun.jdmk/jmxtools 
                                              com.sun.jmx/jmxri]]]
  :main places.core)
