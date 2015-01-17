(require '[clojure.java.io :as io]
         '[clojure.string :refer [split]]
         '[clojure.set :refer [map-invert]])

(defrecord Card [value suit])
(def card-values [\2 \3 \4 \5 \6 \7 \8 \9 \T \J \Q \K \A])

(defn parse-line [l]
  (split-at 5 
            (map #(Card. (.indexOf card-values (first %))
                         (second %)) 
                 (split l #" "))))

; helpers
(defn same-suit? [hand]
  (apply = (map :suit hand)))

(defn consecutive-values? [hand]
  (let [values (sort (map :value hand))]
    (and (apply < values) 
         (= 4 (- (last values) (first values))))))

(defn values-histogram [hand]
  (frequencies (map :value hand)))

(def high-card 
  (memoize 
    (fn [hand]
        (sort-by - (map :value hand)))))

(defn n-of-a-kind [n hand]
  (let [hist (values-histogram hand)
        candidate (apply (partial max-key val) hist)]
    (if (= n (val candidate))
      [(key candidate)
       (high-card (filter #(not= (:value %) (key candidate)) hand))]
      -1)))

(defn v [b] (if b 1 0))

(defn high-cards-if [cond-fn? hand]
  (if (cond-fn? hand) (high-card hand) -1))

; rules
(defn royal-flush? [hand]
  (and (same-suit? hand)
       (= (sort (map :value hand)) (range 8 13))))

(defn straight-flush? [hand]
  (high-cards-if (every-pred same-suit? consecutive-values?) hand))

(def four-of-a-kind? (partial n-of-a-kind 4))

(defn full-house? [hand]
  (let [hist (into (sorted-map) (map-invert (values-histogram hand)))]
    (if (= '(2 3) (keys hist))
      (hist 3)
      -1)))

(defn flush? [hand] (high-cards-if same-suit? hand))

(defn straight? [hand] (high-cards-if consecutive-values? hand))

(def three-of-a-kind? (partial n-of-a-kind 3))

(defn two-pairs? [hand]
  (let [pairs (filter #(= 2 (val %)) (values-histogram hand))]
    (if (= 2 (count pairs))
      (apply max (map first pairs))
      -1)))

(def one-pair? (partial n-of-a-kind 2))

(def rules
  [(comp v royal-flush?) straight-flush? four-of-a-kind? full-house?
   flush? straight? three-of-a-kind? two-pairs? one-pair? high-card])

; game
(defn vec>? [v1 v2]
  (when-let 
    [scores (first
              (drop-while (partial apply =) 
                          (map vector v1 v2)))]
    (v (apply > scores))))

(defn play [[hand1 hand2]]
  (loop [rulz rules]
        (let [r (comp flatten vector (first rulz))
              h1 (r hand1)
              h2 (r hand2)]
          (if-some [result (vec>? h1 h2)]
            result
            (recur (rest rulz))))))

(let [lines (line-seq (io/reader "054.txt"))
      get-hands (map parse-line)
      determine-winner (map play)]
  (transduce (comp get-hands determine-winner) + 0 lines))
