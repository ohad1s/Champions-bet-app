# Champions Betting App

![logo_Champions](https://github.com/ohad1s/Champions-bet-app/assets/92723105/226f83d9-efad-4101-89c8-0c49a29f00af)

Champions is an exciting betting app developed using Android Studio, Java, and Firebase. The app aims to provide sports enthusiasts with an exhilarating experience of legal sports betting, allowing them to compete against friends and make predictions on various sports events.

## Project Objectives
The primary goal of the Champions app is to offer a legitimate and non-addictive solution to the world of sports betting. The app allows users to experience the thrill of betting in a legal and responsible manner while competing with friends.

## Current Situation and the Need for a Solution
Many people around the world enjoy watching sports, and a significant percentage of them engage in sports betting legally. However, some individuals tend to overspend on betting, which can lead to addiction. To address this issue, Champions provides a platform for legal and controlled sports betting among friends. The app ensures a fun and competitive environment without the risk of addiction.

## Key Features
- Create and manage betting tournaments with specified end dates and games.
- Generate a unique token for each tournament to invite friends to participate.
- Players can join multiple tournaments and place free bets to compete against their friends.
- Leaderboards display the points earned by each participant based on correct predictions.
- Tournament administrators can input the actual game results to calculate fair and uniform points distribution.
- Points distribution: Correct outcome - 3 points, Correct winning team - 1 point.
  
## User Access Levels
1. Tournament Administrator: Administrators can create tournaments, set end dates, and add games. They can also invite friends to join the tournament.
2. Player: Players can join tournaments and place free bets to accumulate points. They can view leaderboards and compete for the top position.
   
## Deployment on AWS EC2
The Champions app is deployed on an AWS EC2 server, running a Flask application on port 5000. The app expects an HTTP-GET request with two parameters (email and password) for authentication against Firebase. Based on the login status, the app returns a response code and message.

## Installation and Setup
To install and run the Champions app, follow these steps:

1. Clone the repository: git clone https://github.com/your-username/champions-app.git
2. Open the project in Android Studio.
3. Set up Firebase authentication and database integration.
4. Run the app on an Android emulator or a physical device.
   

## Contributing
Contributions to enhance the Champions app are welcome. To contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch: git checkout -b feature/your-feature
3. Make your changes and commit them: git commit -m 'Add your feature'
4. Push to the branch: git push origin feature/your-feature
5. Submit a pull request explaining your changes.
   
We hope that the Champions app provides a legal and exciting sports betting experience to users, fostering healthy competition among friends without the risks associated with addictive gambling practices.

## ERD:

![ERD](https://github.com/ohad1s/Champions-bet-app/assets/92723105/0ab52d86-f4ff-4396-aab9-f899a4b9643b)

