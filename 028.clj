(println
  (reduce +
          (reductions + 1
                      (mapcat
                        (partial repeat 4)
                        (filter even? (range 2 1001))))))
