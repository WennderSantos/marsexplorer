(ns marsexplorer.core
  (:require [marsexplorer.adapters :as adapters]
            [marsexplorer.specs :as specs]
            [marsexplorer.service :as service]
            [marsexplorer.controller :as ctrl]))

(defn -main
  ([]
    (println "Missing file path with the configuration needed to start"))
  ([& args]
    (try
      (->> (service/get-file-content! (first args))
           (adapters/file-content->settings! specs/mars-bottom-left-coord)
           (ctrl/handle-settings specs/cardinal-directions)
           (map #(println %))
           (doall))
      (catch Exception e
        (-> (str "Caught exception: " (.getMessage e))
            (println))))))