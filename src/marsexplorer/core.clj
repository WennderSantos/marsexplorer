(ns marsexplorer.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [marsexplorer.adapters :as adapters]))

(defn start [settings]
  (let [explorers-config (adapters/settings->explorers-config settings)
        mars (first settings)]))

(defn -main
  ([] (println "You need to inform the file path which have the configuration needed to start"))
  ([& args]
    (start (str (str/split-lines (slurp (first args)))))))
