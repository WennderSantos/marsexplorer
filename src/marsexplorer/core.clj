(ns marsexplorer.core
  (:require [marsexplorer.adapters :as adapters]
            [marsexplorer.controller :as ctrl])
(:gen-class))

(defn -main
  ([]
    (println "Missing file path with the configuration needed to start"))
  ([& args]
    (try
      (->> (slurp (first args))
           (adapters/file-content->settings!)
           (ctrl/handle-settings)
           (map #(println %))
           (doall))
      (catch Exception e
        (-> (str "Caught exception: " (.getMessage e))
            (println))))))