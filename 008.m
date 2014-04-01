(* ::Package:: *)

input = Map[ToExpression, 
	StringSplit[StringReplace[
		Import["/Users/daniel/Projects/euler/008.txt"], {WhitespaceCharacter -> ""}], ""]];

fives = Map[Take[input, {#, # + 4}] &, Range[996]];
Max[Map[Product[i, {i, #}] &, fives]]
