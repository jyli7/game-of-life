(ns game-of-life.core)

(use '[clojure.set :only [union difference]])

(def live-cells-test #{[2 1] [2 3] [0 0] [1 3] [2 2] [3 3]})

(defn neighbors [[x y]]
	(disj (set (for [i (range (- x 1) (+ x 2))
		  j (range (- y 1) (+ y 2))]
		  [i j])) [x y]))

(defn live-neighbors [cell, live-cells]
	(set (filter live-cells (neighbors cell))))

(defn dead-neighbors [cell, live-cells]
	(difference (neighbors cell) (live-neighbors cell live-cells)))

(defn num-live-neighbors
	"Takes in a cell (coordinate pair),
	and the complete set of ACTIVE cells, return the num of ACTIVE neighbors"
	[cell, live-cells]
	(count (live-neighbors cell live-cells)))

(defn num-dead-neighbors
	[cell, live-cells]
	(count (dead-neighbors cell live-cells)))

(defn stay-alive? [cell live-cells]
	(#{2 3} (num-live-neighbors cell live-cells)))

(defn come-alive? [cell live-cells]
	(= 3 (num-live-neighbors cell live-cells)))

(defn next-live-cells [live-cells]
	(let [all-dead-neighbors (apply union (map #(dead-neighbors % live-cells) live-cells))]
		(set (concat
			(filter #(come-alive? % live-cells) all-dead-neighbors)
			(filter #(stay-alive? % live-cells) live-cells)))))
