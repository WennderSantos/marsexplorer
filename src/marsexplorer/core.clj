(ns marsexplorer.core
  (:require [clojure.string :as str]
            [marsexplorer.adapters :as adapters]
            [marsexplorer.specs :as specs]
            [marsexplorer.controller :as ctrl]))

(defn -main
  ([]
    (println "Missing file path with the configuration needed to start"))
  ([& args]
    (doall
      (->> (str/split-lines (slurp (first args)))
           (adapters/settings->explorers-config)
           (ctrl/handle-explorers)
           (map #(println %))))))

