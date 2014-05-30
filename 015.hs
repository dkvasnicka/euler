import Math.Combinat.Partitions
import Data.List

data Segments = Segments { intermediateSum :: Int, segs :: [[Int]] } deriving Show

reduce :: Segments -> Segments -> Segments
reduce x y = Segments ((intermediateSum x) + ((llength ^ 2) + (llength * length rsegs))) rsegs
                where
                    llength = length (segs x)
                    rsegs = segs y

main :: IO ()
main = print $ 
          (intermediateSum folded + 1) * 2
              where 
                comps = map (nub . permutations . fromPartition) (partitions 20)
                groupedComps = groupBy (\x y -> length (head x) == length (head y)) comps
                folded = foldl1 reduce (map ((Segments 0) . concat) groupedComps)
