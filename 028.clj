
(reduce +
        (reduce
          (fn [accum in] (cons (+ in (first accum)) accum))
          '(1)
          (mapcat 
            (partial repeat 4) 
            (filter even? (range 2 1001)))))
