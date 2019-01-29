(ns marsexplorer.service)

(defn get-file-content! [file-path]
  (slurp file-path))