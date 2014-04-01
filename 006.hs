problem6 :: [Int] -> Int
problem6 nums = (foldl (+) 0 nums) ^ 2 - (foldl (\ x y -> x + y ^ 2) 0 nums)

main = print (problem6 [1..100])
