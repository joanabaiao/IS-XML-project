
for $x in doc("files/movies.xml")/catalog/movie
where $x/year>2015 and $x/genres/item="Comedy"
return $x

