(println
  (reduce + 
          (distinct 
            (into (range 5 1000 5) 
                  (range 3 1000 3)))))
