(require '[clojure.core.reducers :as r])

(defn fibos [limit]
    (take-while 
      #(< (last %) limit) 
      (iterate 
        #(let [newnum (reduce + %)] 
            (vector newnum (+ newnum (last %)))) 
        [1 2])))

(reduce + (r/map #(reduce + (r/filter even? %)) (fibos 4000000)))
