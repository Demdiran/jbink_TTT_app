import React, { useState } from "react";
import { StartGame } from "./mancala/StartGame";
import { Play } from "./mancala/Play";
import { GameState, Player } from "./mancala/gameState";

export function App() {

    const [ gameState, setGameState ] = useState<GameState | undefined>(undefined);
    const [ errorMessage, setErrorMessage ] = useState("");

    async function tryStartGame(playerOne: string, playerTwo: string) {
        if (!playerOne) {
            setErrorMessage("Player 1 name is required!");
            return;
        }

        if (!playerTwo) {
            setErrorMessage("Player 2 name is required!");
            return;
        }

        setErrorMessage("");

        try {
            const response = await fetch('mancala/api/players', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nameplayer1: playerOne , nameplayer2: playerTwo })
            });
    
            if (response.ok) {
                const gameState = await response.json();
                setGameState(gameState);
            }
            setErrorMessage("Failed to start the game. Try again.");
        } catch (error) {
            setErrorMessage(error.toString());
        }
    }

    async function doMove(player: Player, pitNumber: number){
        if(player.hasTurn && pitNumber != 6 && pitNumber != 13){
            const response = await fetch('mancala/api/play/' + pitNumber, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json'
                }
            });
            const gameState = await response.json();
            setGameState(gameState);
        }

    }

    if (!gameState) {
        return <StartGame onPlayersConfirmed={tryStartGame}
                          message={errorMessage}
        />
    }

    return <Play gameState={gameState} doMove={doMove} />
}