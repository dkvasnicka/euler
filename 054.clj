(require '[clojure.java.io :as io]
         '[clojure.string :refer [split]]
         '[clojure.set :refer [map-invert]])

(defrecord Card [value suit])

(defn parse-line [l]
  (split-at 5 
            (map #(apply ->Card (map (comp keyword str) %)) 
                 (split l #" "))))

(def card-values [:2 :3 :4 :5 :6 :7 :8 :9 :T :J :Q :K :A])

; helpers
(defn same-suit? [hand]
  (apply = (map :suit hand)))

(defn consecutive-values? [hand]
  (let [values (sort-by #(.indexOf card-values %) (map :value hand))
        idx (.indexOf card-values (first values))]
    (and (< idx 9) 
         (= values 
            (subvec card-values idx (+ idx 5))))))

(defn values-histogram [hand]
  (frequencies (map :value hand)))

(defn high-card [hand]
  (sort-by -
    (map (comp #(.indexOf card-values %) :value) 
         hand)))

(defn n-of-a-kind [n hand]
  (let [hist (values-histogram hand)
        candidate (apply (partial max-key val) hist)]
    (if (= n (val candidate))
      [(.indexOf card-values (key candidate))
       (high-card (filter #(not= (:value %) (key candidate)) hand))]
      -1)))

; rules
(defn v [b] (if b 1 0))

(defn royal-flush? [hand]
  (and (same-suit? hand)
       (= (map :value hand) 
          (subvec card-values 8))))

(defn straight-flush? [hand]
  (if (and (same-suit? hand)
           (consecutive-values? hand))
    (high-card hand)
    -1))

(def four-of-a-kind? (partial n-of-a-kind 4))

(defn full-house? [hand]
  (let [hist (into (sorted-map) (map-invert (values-histogram hand)))]
    (if (= '(2 3) (keys hist))
      (.indexOf card-values (hist 3))
      -1)))

(defn flush? [hand] 
  (if (same-suit? hand)
    (high-card hand)
    -1))

(defn straight? [hand] 
  (if (consecutive-values? hand)
    (high-card hand)
    -1))

(def three-of-a-kind? (partial n-of-a-kind 3))

(defn two-pairs? [hand]
  (let [pairs (filter #(= 2 (val %)) (values-histogram hand))]
    (if (= 2 (count pairs))
      (apply max (map (comp #(.indexOf card-values %) first) pairs))
      -1)))

(def one-pair? (partial n-of-a-kind 2))

(def rules
  [(comp v royal-flush?) straight-flush? four-of-a-kind? full-house?
   flush? straight? three-of-a-kind? two-pairs? one-pair? high-card])

(defn vec>? [v1 v2]
  (apply >
         (first
           (drop-while (partial apply =) (map vector v1 v2)))))

; game
(defn play [[hand1 hand2]]
  (loop [rulz rules]
        (let [r (comp flatten vector (first rulz))
              h1 (r hand1)
              h2 (r hand2)
              draw (= h1 h2)]
          (if-not draw
                  (v (vec>? h1 h2))
                  (recur (rest rulz))))))

(let [lines (line-seq (io/reader "054.txt"))
      get-hands (map parse-line)
      determine-winner (map play)]
  (transduce (comp get-hands determine-winner) + 0 lines))
