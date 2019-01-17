(ns marsexplorer.adapters)

(defn- string->position [string]
	{:x (Integer/parseInt (str (first string)))
   :y (Integer/parseInt (str (second string)))
	 :direction (keyword (str (last string)))})

(defn settings->explorers-config [settings]
  (loop [settings (rest settings)
         count 0
         aux {}
         result []]
    (cond
      (empty? settings)
        result
      (= count 1)
        (recur (rest settings)
               0
               {}
               (conj result
                     (assoc aux :instructions
								                (vec
                                  (map #(keyword (str %))
									 		                 (first settings))))))
      :else
        (recur (rest settings)
               (inc count)
               (assoc aux :position
                          (string->position (filter #(not= \space %)
								 									                  (first settings))))
               result))))
