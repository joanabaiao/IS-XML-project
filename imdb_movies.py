from imdb import IMDb

# Create an instance of the IMDb class
ia = IMDb('http')

# Getting top 250 movies
search = ia.get_top250_movies()

# Create XML code
xml_text = "<?xml version='1.0' encoding='utf-8'?>\n<catalog>"
for m in search:

    movieid = m.movieID
    movie = ia.get_movie(movieid)

    # Select 5 actors
    cast = movie['cast'][0:5]
    xml_cast = '<cast>'
    for i in range(0, 5):
        actor = cast[i]
        xml_cast = xml_cast + '<person id="' + actor.personID + '">' + '<name>' + actor['name'] + '</name></person>'
    xml_cast = xml_cast + '</cast>'

    # Select country
    countries = movie['country']
    xml_countries = '<countries>'
    for i in range(0, len(countries)):
        xml_countries = xml_countries + '<item>' + countries[i] + '</item>'
    xml_countries = xml_countries + '</countries>'

    # Select directors
    directors = movie['directors']
    xml_directors = '<director>'
    for i in range(0, len(directors)):
        director = directors[i]
        xml_directors = xml_directors + '<person id="' + director.personID + '">' + '<name>' + director['name'] + '</name></person>'
    xml_directors = xml_directors + '</director>'

    # Select genres
    genres = movie['genres']
    xml_genres = '<genres>'
    for i in range(0, len(genres)):
        xml_genres = xml_genres + '<item>' + genres[i] + '</item>'
    xml_genres = xml_genres + '</genres>'

    xml_movie = '<movie id="' + movieid + '">' + \
        '<top-250-rank>' + str(movie['top 250 rank']) + '</top-250-rank>' + \
        '<title>' + movie['title'] + '</title>' + \
        '<runtimes>' + str(movie['runtimes'][0]) + '</runtimes>' + \
        '<year>' + str(movie['year']) + '</year>' + \
        '<rating>' + str(movie['rating']) + '</rating>' + \
        '<votes>' + str(movie['votes']) + '</votes>' + \
        '<description>' + movie['plot outline'] + '</description>' + \
        '<cover-url>' + movie['cover url'] + '</cover-url>' + xml_genres + xml_directors + xml_cast + xml_countries + '</movie>'
    
    xml_text = xml_text + xml_movie

    print(movie['top 250 rank'])

xml_text = xml_text + '</catalog>'

# Create and write an XML file
with open("movies.xml", "w") as new_file:
    new_file.write(xml_text)
    new_file.close()
