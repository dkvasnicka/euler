(defn check-factors [x]
  (not (empty? (filter
                 #(and (>= % 100) (<= % 999))
                 (map #(/ x %)
                      (filter #(= 0 (mod x %)) (range 999 99 -1)))))))

(println
  (take 1
        (filter check-factors
                (filter
                  #(let [numstr (str %)]
                     (= numstr (clojure.string/reverse numstr)))
                  (range 997799 10000 -1)))))
