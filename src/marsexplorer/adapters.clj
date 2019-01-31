(ns marsexplorer.adapters
  (:require [clojure.string :as str]
            [marsexplorer.config :as config]
            [clojure.spec.alpha :as s]))

(defn- line->position! [line]
  {:pre [(s/assert ::config/position-input line)]}
    {:x         (Integer/parseInt (first line))
     :y         (Integer/parseInt (second line))
     :direction (keyword (last line))})

(defn- line->instructions! [line]
  {:pre [(s/assert ::config/instructions-input line)]}
  (->> (map #(keyword (str %)) line)
       (vec)))

(defn- line->mars-length! [line {:keys [x y]}]
  {:pre [(s/assert ::config/coord-input line)]}
  {:bottom-left {:x x
                 :y y}
   :top-right   {:x (Integer/parseInt (first line))
                 :y (Integer/parseInt (second line))}})

(defn file-content->settings! [file-content]
  (let [lines (str/split-lines file-content)]
    {:mars-length (line->mars-length! (-> (first lines)
                                          (str/split #" "))
                                          config/mars-bottom-left-coord)
     :explorers (->> (rest lines)
                     (partition 2)
                     ((fn [lines-config]
                       (map
                         (fn [[line-position line-instructions]]
                           (assoc {}
                                  :position (-> line-position
                                                (str/split #" ")
                                                (line->position!))
                                  :instructions (-> line-instructions
                                                    (line->instructions!))))
                          lines-config))))}))

(defn position->cmdline-fmt [{:keys [x y direction]}]
  "Returns the values of a position separated by space"
  "Ex: {:x 1 :y 2 :direction :N} will return 1 2 N"
  (str x " " y " " (name direction)))