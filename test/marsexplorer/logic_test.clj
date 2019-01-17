(ns marsexplorer.logic_test
  (:require [midje.sweet :refer :all]
            [marsexplorer.specs :as specs]
            [marsexplorer.logic :as logic]))

(fact "execute-instructions"
	(logic/execute-instructions {:x 1 :y 2 :direction :N}
												 [:L :M :L :M :L :M :L :M :M]
												 specs/directions) => {:direction :N :x 1 :y 3}

	(logic/execute-instructions {:x 3 :y 3 :direction :E}
												 [:M :M :R :M :M :R :M :R :R :M]
												 specs/directions) => {:direction :E :x 5 :y 1})

