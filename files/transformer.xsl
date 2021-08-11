<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link href="style.css" rel="stylesheet" type="text/css"/>
                <title>Movie Catalog</title>
                <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;600;700&amp;display=swap" rel="stylesheet"/>
                <script src="main.js"></script>
            </head>

            <body>
                <div class="top">

                    <!-- TITLE -->
                    <h1>Movies</h1>

                    <!-- STATISTICS -->
                    <xsl:apply-templates select="//statistics"/>

                </div>

                <!-- MOVIES TOP -->
                <div class="headline">
                    <h3>Selected Movies by Director</h3>
                    <hr/>
                </div>

                <!-- MOVIES BY DIRECTOR  -->
                <div class="director">
                    <xsl:apply-templates select="director-catalog/director"/>
                </div>

            </body>


            <footer>
                <br/>
                <h4>Systems Integration - Project 1</h4>
                <span class="br"></span>
                <p>André Bernardes 2017248159</p>
                <p>Joana Baião 2017260526</p>
            </footer>

        </html>

    </xsl:template>

    <!-- STATISTICS -->
    <xsl:template match="statistics">

        <div class="statistics">

            <div class="flex-container">
                <h3>
                    <xsl:value-of select="number-movies"/>
                </h3>
                <h3>
                    <xsl:value-of select="number-directors"/>
                </h3>
            </div>

            <div class="flex-container">
                <p>Movies</p>
                <p>Directors</p>
            </div>
        </div>

        <!-- TOP DIRECTORS-->
        <div class="headline">
            <h3>Top Directors</h3>
            <hr/>
        </div>

        <table class="styled-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Rank Position</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="ranking/top-directors">
                    <tr>
                        <td>
                            <xsl:value-of select="name"/>
                        </td>
                        <td>
                            <xsl:value-of select="best-rank"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>

    </xsl:template>

    <!-- MOVIES -->
    <xsl:template match="movie">

        <div class="movie">

            <img src="{cover-url}" alt="movie cover"/>

            <div class="movies-details">
                <h3>
                    <xsl:value-of select="title"/>
                </h3>

                <h4>Year:</h4>
                <p>
                    <xsl:value-of select="year"/>
                </p>
                <span class="br"></span>

                <h4>Rating:</h4>
                <p>
                    <xsl:value-of select="rating"/>
                </p>
                <pre> (rated by <xsl:value-of select="votes"/> people)</pre>
                <span class="br"></span>

                <h4>Rank position:</h4>
                <p>
                    <xsl:value-of select="top-250-rank"/>
                </p>
                <span class="br"></span>

                <h4>Runtime:</h4>
                <pre><xsl:value-of select="runtimes"/> minutes</pre>
                <span class="br"></span>

                <h4>Countries:</h4>
                <xsl:for-each select="countries/*">
                    <p>
                        <xsl:value-of select="concat(., substring(', ', 2 - (position() != last())))"/>
                    </p>
                </xsl:for-each>
                <span class="br"></span>

                <h4>Genres:</h4>
                <xsl:for-each select="genres/*">
                    <p>
                        <xsl:value-of select="concat(., substring(', ', 2 - (position() != last())))"/>
                    </p>
                </xsl:for-each>
                <span class="br"></span>

                <h4>Cast:</h4>
                <xsl:for-each select="cast/*">
                    <p>
                        <xsl:value-of select="concat(., substring(',', 2 - (position() != last())))"/>
                    </p>
                </xsl:for-each>
                <span class="br"></span>

                <h4>Directors:</h4>
                <xsl:for-each select="director/*">
                    <p>
                        <xsl:value-of select="concat(., substring(',', 2 - (position() != last())))"/>
                    </p>
                </xsl:for-each>
                <span class="br"></span>

                <h4>Description:</h4>
                <p>
                    <xsl:value-of select="description"/>
                </p>
            </div>

        </div>
    </xsl:template>

    <!-- DIRECTORS -->
    <xsl:template match="director">

        <h2>
            <xsl:value-of select="name"/>
        </h2>
        <div class="divider-transparent"></div><br/>

        <xsl:apply-templates select="movie"/>

    </xsl:template>


</xsl:stylesheet>