(ns marsexplorer.core
  (:require [marsexplorer.adapters :as adapters]
            [marsexplorer.service :as service]
            [marsexplorer.controller :as ctrl])
(:gen-class))

(defn -main
  ([]
    (println "Missing file path with the configuration needed to start"))
  ([& args]
    (try
      (->> (service/get-file-content! (first args))
           (adapters/file-content->settings!)
           (ctrl/handle-settings)
           (map #(println %))
           (doall))
      (catch Exception e
        (-> (str "Caught exception: " (.getMessage e))
            (println))))))