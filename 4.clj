(defn check-factors [x]
   (not (empty? (filter 
                  #(= 3 (count (str %))) 
                  (map #(/ x %) 
                       (filter #(= 0 (mod x %)) (range 999 99 -1)))))))

(take 1 
      (filter check-factors 
              (filter 
                #(let [numstr (str %)] 
                          (= numstr (clojure.string/reverse numstr))) 
                (range 997799 10000 -1))))
