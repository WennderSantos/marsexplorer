(ns marsexplorer.controller-test
  (:require [midje.sweet :refer :all]
            [marsexplorer.controller :as ctrl]
            [marsexplorer.config :refer [mars-bottom-left-coord]]))

(def ^:private mars-length {:bottom-left mars-bottom-left-coord
                            :top-right {:x 10 :y 10}})

(def ^:private result-valid-position [{:position {:x 3
                                                  :y 3
                                                  :direction :N}
                                       :instructions [:M
                                                      :R
                                                      :M
                                                      :L
                                                      :M]}])
(def ^:private result-invalid-position [{:position {:x 1
                                                    :y 1
                                                    :direction :S}
                                         :instructions [:M
                                                        :R
                                                        :M
                                                        :M
                                                        :L]}])

(fact "Handle settings"
  (fact "Should return position after instructions execute"
    (ctrl/handle-settings {:mars-length mars-length
                           :explorers result-valid-position})
                          => '("4 5 N"))

  (fact "Should return invalid position after instructions execute"
    (ctrl/handle-settings {:mars-length mars-length
                           :explorers result-invalid-position})
                          => '("Invalid position -1 0 W")))

