(ns marsexplorer.config
  (:require [clojure.spec.alpha :as s]))

(s/check-asserts true)

(defn- isNumber? [s]
  (re-matches #"^[0-9]*$" s))

(defn- instructions? [s]
  (re-matches #"^[LRM]+$" s))

(def cardinal-directions-turns
  {:L {:N :W
       :E :N
       :S :E
       :W :S}
   :R {:N :E
       :E :S
       :S :W
       :W :N}})

(def ^:private ^:const cardinal-directions
  (->> (:L cardinal-directions-turns)
       (keys)
       (map #(name %))
       (clojure.string/join)))

(defn- cardinal-directions? [s]
  (as-> (re-pattern (str "^["
                         cardinal-directions
                         "]{1}$")) regex
        (re-matches regex s)))

(def ^:const mars-bottom-left-coord {:x 0 :y 0})

(s/def ::coord-input (s/tuple isNumber? isNumber?))

(s/def ::position-input (s/tuple isNumber? isNumber? cardinal-directions?))

(s/def ::instructions-input instructions?)