(ns marsexplorer.service)

(defn read-file [file-path]
  (slurp file-path))