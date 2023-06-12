const movies = require('C:/Users/mehme/sqlcinema_movie.json');


const statement = "CALL create_ticket(?, ?, TIMESTAMP('?'), ?);";




const fs = require('fs');

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getRandomPrice() {
    return getRandomNumber(1, 20) * 5;
}

function getRandomTimestamp(maxDate) {
    const date = new Date(maxDate);

    date.setHours(getRandomNumber(8, 23));
    date.setMinutes(Math.random() < 0.5 ? 0 : 30);
    return date
        .toISOString().slice(0, 19).replace('T', ' ');
}


const ONE_WEEK = 7 * 24 * 60 * 60 * 1000;

const createMovieTicket = () => {
    const statements = [];
    for (let movie of movies) {
        const releaseDate = new Date(movie.release_date);
        const randomPrice = getRandomPrice();

        for (let i = 0; i < getRandomNumber(1, 10); i++) {

            statements.push(statement
                .replace('?', movie.movie_id)
                .replace('?', `${getRandomNumber(1, 10)}`)
                .replace('?', getRandomTimestamp(releaseDate.getTime() + ONE_WEEK * getRandomNumber(1, 6)))
                .replace('?', randomPrice));
        }
    }
    return statements;
}

fs.writeFileSync('C:/Users/mehme/OneDrive/Documents/personal-projects/cinema-ticket-management/database/sql/inserts/08_tickets.sql',
    createMovieTicket().join('\n'));