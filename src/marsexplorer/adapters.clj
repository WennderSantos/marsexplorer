(ns marsexplorer.adapters
  (:require [clojure.string :as str]
            [marsexplorer.specs :as specs]))

(defn- line->position! [string]
	{:x         (Integer/parseInt (str (first string)))
   :y         (Integer/parseInt (str (second string)))
	 :direction (keyword (str (last string)))})

(defn- line->mars-length! [line {:keys [x y]}]
  {:bottom-left {:x x
                 :y y}
   :top-right   {:x (Integer/parseInt (str (first line)))
                 :y (Integer/parseInt (str (last line)))}})

(defn- lines->explorers! [lines]
  (loop [lines  lines
         count  0
         aux    {}
         result []]
    (cond
      (empty? lines)
        result
      (= count 1)
        (recur (rest lines)
               0
               {}
               (conj result
                     (assoc aux :instructions
								                (vec
                                  (map #(keyword (str %))
									 		                 (first lines))))))
      :else
        (recur (rest lines)
               (inc count)
               (assoc aux :position
                          (line->position! (filter #(not= \space %)
								 									                (first lines))))
               result))))

(defn file-content->settings! [mars-bottom-left-coord file-content]
  (let [lines       (str/split-lines file-content)
        mars-length (line->mars-length! (first lines)
                                       mars-bottom-left-coord)
        explorers   (lines->explorers! (rest lines))]
    {:mars-length mars-length
     :explorers   explorers}))

(defn position->cmdline-fmt [{:keys [x y direction]}]
  "Returns the values of a position separated by space"
  "Ex: {:x 1 :y 2 :direction :N} will return 1 2 N"
  (str x " " y " " (name direction)))