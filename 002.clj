(require '[clojure.core.reducers :as r])

; Attempt at a 100 % lazy solution
(defn fibos [limit]
  (take-while
    #(< (last %) limit)
    (iterate
      #(let [newnum (reduce + %)]
         (vector newnum (+ newnum (last %))))
      [1 2])))

(println
  (reduce + (r/map #(reduce + (r/filter even? %)) (fibos 4000000))))
