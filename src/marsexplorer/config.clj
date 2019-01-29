(ns marsexplorer.config
  (:require [clojure.spec.alpha :as s]))

(s/check-asserts true)

(defn- isNumber? [s]
  (re-matches #"^[0-9]*$" s))

(defn- instructions? [s]
  (re-matches #"^[LRM]+$" s))

(defn- cardinal-directions? [s]
  (re-matches #"^[SENW]{1}$" s))

(def cardinal-directions '(:N :E :S :W))

(def mars-bottom-left-coord {:x 0 :y 0})

(s/def ::coord-input (s/tuple isNumber? isNumber?))

(s/def ::position-input (s/tuple isNumber? isNumber? cardinal-directions?))

(s/def ::instructions-input instructions?)