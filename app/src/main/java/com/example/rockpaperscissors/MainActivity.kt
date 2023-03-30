package com.example.rockpaperscissors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rockpaperscissors.ui.theme.RockPaperScissorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RockPaperScissorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    RockPaperScissorApp()
                }
            }
        }
    }

@Composable
fun RockPaperScissorApp(){
    var wins by remember{
        mutableStateOf(0)
    }
    var draws by remember{
        mutableStateOf(0)
    }
    var losses by remember{
        mutableStateOf(0)
    }

    var computerHand by remember {
        mutableStateOf(0)
    }
    var playerHand by remember {
        mutableStateOf(0)
    }

    var showAllHands by remember {
        mutableStateOf(false)
    }

    val rock: () -> Unit = {
        playerHand = 1
        computerHand = (1..3).random()
        showAllHands = true
    }
    val paper: () -> Unit = {
        playerHand = 2
        computerHand = (1..3).random()
        showAllHands = true
    }
    val scissor: () -> Unit = {
        playerHand = 3
        computerHand = (1..3).random()
        showAllHands = true
    }

    val playAgain: () -> Unit = {
        if(playerHand == 1 && computerHand ==2)
            losses++
        else if (playerHand == 2 && computerHand == 3)
            losses++
        else if (playerHand == 3 && computerHand == 1)
            losses++
        else if (playerHand == 1 && computerHand == 1)
            draws++
        else if (playerHand == 2 && computerHand == 2)
            draws++
        else if (playerHand == 3 && computerHand == 3)
            draws++
        else
            wins++

        showAllHands = false
        computerHand = 0
        playerHand = 0
    }

    MainScreen(
        wins = wins,
        draws = draws,
        losses = losses,
        computerHand = computerHand,
        playerHand = playerHand,
        showAllHands = showAllHands,
        onRockButtonClick = rock,
        onPaperButtonClick = paper,
        onScissorButtonClick = scissor,
        onPlayAgainButtonClick = playAgain
    )


}


@Composable
fun MainScreen(
    wins: Int,
    draws: Int,
    losses: Int,
    computerHand: Int,
    playerHand: Int,
    showAllHands: Boolean,
    onRockButtonClick: () -> Unit,
    onPaperButtonClick: () -> Unit,
    onScissorButtonClick: () -> Unit,
    onPlayAgainButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()){
        // Background Image
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween){
            // Scoreboard
            Scoreboard(wins = wins, draws = draws, losses = losses)
            // Plays
            Row(modifier = Modifier.fillMaxWidth()) {
                PlayerHand(hand = playerHand, showAllHands = showAllHands)
                ComputerHand(hand = computerHand, showAllHands = showAllHands)
            }
            if (showAllHands){
                Result(computerHand = computerHand, playerHand = playerHand)
                PlayAgainButton(playAgainButton = onPlayAgainButtonClick)
            }
            // Buttons
            PlayButtons(
                buttonRock = onRockButtonClick,
                buttonPaper = onPaperButtonClick,
                buttonScissor = onScissorButtonClick,
                showAllHands = showAllHands
            )

        }
    }

}

@Composable
fun Scoreboard(wins: Int, draws: Int, losses: Int){
    // Row containing the scoreboard
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "Wins: $wins")
        Text(text = "Draws: $draws")
        Text(text = "Losses: $losses")
    }
}

@Composable
fun PlayerHand(
    hand: Int,
    showAllHands: Boolean
) {
    if (showAllHands) {
        Column{
            val handImage = when (hand) {
                1 -> R.drawable.player_rock
                2 -> R.drawable.player_paper
                else -> {
                    R.drawable.player_scissor
                }
            }
            Image(painter = painterResource(id = handImage), contentDescription = "player hand")
        }
    }
}
@Composable
fun ComputerHand(
    hand: Int,
    showAllHands: Boolean
){
    if (showAllHands) {
        Column{
            val handImage = when (hand) {
                1 -> R.drawable.computer_rock
                2 -> R.drawable.computer_paper
                else -> {
                    R.drawable.computer_scissor
                }
            }
            Image(painter = painterResource(id = handImage), contentDescription = "computer hand")
        }
    }
}

@Composable
fun PlayButtons(buttonRock: () -> Unit,
                buttonPaper: () -> Unit,
                buttonScissor: () -> Unit,
                showAllHands: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(bottom = 120.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = buttonRock, enabled = !showAllHands) {
            Text(text = "Rock")
        }
        Button(onClick = buttonPaper, enabled = !showAllHands) {
            Text(text = "Paper")
        }
        Button(onClick = buttonScissor, enabled = !showAllHands) {
            Text(text = "Scissor")
        }
    }
}

@Composable
fun PlayAgainButton(playAgainButton: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = playAgainButton) {
            Text(text = "Play Again")
        }
    }
}

@Composable
fun Result(computerHand: Int, playerHand: Int){
    val result = if(playerHand == 1 && computerHand == 2)
        "You Lost!"
    else if (playerHand == 2 && computerHand == 3)
        "You Lost!"
    else if (playerHand == 3 && computerHand == 1)
        "You Lost!"
    else if (playerHand == 1 && computerHand == 1)
        "It's a Draw!"
    else if (playerHand == 2 && computerHand == 2)
        "It's a Draw!"
    else if (playerHand == 3 && computerHand == 3)
        "It's a Draw!"
    else
        "You Won!"

    Row(
        modifier = Modifier
            .fillMaxWidth()
        ,horizontalArrangement = Arrangement.Center
    ) {
        Text(text = result, color = Color.Blue, fontWeight = Bold, fontSize = 32.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RockPaperScissorsTheme {
        RockPaperScissorApp()
    }
}
}
