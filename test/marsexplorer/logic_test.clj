(ns marsexplorer.logic_test
  (:require [midje.sweet :refer :all]
            [marsexplorer.specs :as specs]
            [marsexplorer.logic :as logic]))

(fact "Turn explorer face direction"
	(fact "to the left when facing north"
		(logic/turn :L
								specs/directions
								:N) => :W)
	(fact "to the right when facing west"
		(logic/turn :R
								specs/directions
								:W) => :N)
	(fact "to the left when facing east"
		(logic/turn :L
								specs/directions
								:E) => :N)
	(fact "to the right when facing south"
		(logic/turn :R
							  specs/directions
							  :S) => :W)
	(fact "to the right when facing north"
		(logic/turn :R
							  specs/directions
							  :N) => :E)
	(fact "to the right when facing east"
		(logic/turn :R
							  specs/directions
							  :E) => :S))

(fact "move"
	(let [position {:x 1 :y 1}]
		(fact "when facing north"
				(logic/move (assoc position :direction :N)) => {:direction :N :x 1 :y 2})
		(fact "when facing east"
				(logic/move (assoc position :direction :E)) => {:direction :E :x 2 :y 1})
		(fact "when facing south"
				(logic/move (assoc position :direction :S)) => {:direction :S :x 1 :y 0})
		(fact "when facing west"
				(logic/move (assoc position :direction :W)) => {:direction :W :x 0 :y 1})))

(fact "execute-actions"
	(logic/execute-actions {:x 1 :y 2 :direction :N}
												 [:L :M :L :M :L :M :L :M :M]
												 specs/directions) => {:direction :N :x 1 :y 3}

	(logic/execute-actions {:x 3 :y 3 :direction :E}
												 [:M :M :R :M :M :R :M :R :R :M]
												 specs/directions) => {:direction :E :x 5 :y 1})

