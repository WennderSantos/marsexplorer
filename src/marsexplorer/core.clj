(ns marsexplorer.core
  (:require [clojure.string :as str]
            [marsexplorer.adapters :as adapters]
            [marsexplorer.specs :as specs]
            [marsexplorer.logic :as logic]))

(defn start-and-execute [settings]
  (->> settings
       (adapters/settings->explorers-config)
       (map #(logic/execute-actions (:position %)
                                    (:actions %)
                                    specs/directions))
       (map #(vals %))))

(defn -main
  ([] (println "You need to inform the file path which have the configuration needed to start"))
  ([& args]
    (-> (start-and-execute (str/split-lines (slurp (first args))))
        (println))))
