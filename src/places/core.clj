(ns places.core
  (:require [compojure.core :as c]
            [ring.adapter.jetty :as ring]
            [korma.db :as db]
            [korma.core :refer :all]
            [clojure.pprint :as p]
            [clojure.string :as s])
  (:gen-class))

(defn url->db-spec
  [url mode]
  (let [uri (java.net.URI. url)
        user-info (.getUserInfo uri)
        [user password] (when user-info (s/split user-info #":"))]
    {:host (.getHost uri)
     :port (.getPort uri)
     :user user
     :password password
     :db (apply str (rest (.getPath uri)))
     :ssl true
     :sslfactory (when (= mode :dev) 
                   "org.postgresql.ssl.NonValidatingFactory")}))


(defn- connect-to-db
  []
  (println "HOLY SHOT!!!!!!" (System/getenv "DATABASE_URL")) 
  (db/defdb db (db/postgres (url->db-spec 
                              (System/getenv "DATABASE_URL") 
                              :dev))))

(defentity place
  (pk :id)
  (table :place))

(c/defroutes routes
  (c/GET "/db" [k] (with-out-str
                     (p/pprint (:options db))))
  (c/GET "/" [] (str (select place))))

(defn -main
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  #_(p/pprint (select place))
  (connect-to-db)
  ;; starting jetty !
  (ring/run-jetty routes {:port (or (Integer/parseInt
                                      (System/getenv "PORT"))
                                    5000) :join? false}))
