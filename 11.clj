(require '[clojure.string :as s])

(def input 
   (mapv #(mapv (fn [n] (Integer/parseInt n)) (s/split % #" ")) 
          (vec (s/split-lines (slurp "11.txt")))))

(defn diagonals [mx]
  (let [size (count (first mx))]
    (map
      #(map nth mx ((comp % range) size)) 
      [reverse identity])))

(defn transpose [mx]
  (apply map vector mx))

(defn svec [start size v] (subvec v start (+ start size)))

(defn select-square [mx x y size]
  (map (partial svec y size) 
       (svec x size mx)))

(def product (partial reduce *))

(let [rng (range 0 16)
      mxs (for [x rng y rng] (select-square input x y 4))]
  (apply max 
         (map (fn [mx] 
                (apply max
                       (concat
                         (map product mx)
                         (map product (transpose mx))
                         (map product (diagonals mx))))) 
              mxs)))
