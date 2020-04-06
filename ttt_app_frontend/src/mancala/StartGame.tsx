import React, { useState } from "react";
import styled from "styled-components";

interface StartGameProps {
    message: string;
    onPlayersConfirmed(playerOne: string, playerTwo: string): void;
}

// a button element with the specified css style applied to it
const StartButton = styled.button`
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`

// a p element with the specified css style applied to it
const ErrorMessage = styled.p`
    height: 1em;
    color: red;
`;

/**
 * Allows the players to enter their name.
 */
export function StartGame({ message, onPlayersConfirmed }: StartGameProps) {

    const [ playerOne, setPlayerOne ] = useState("");
    const [ playerTwo, setPlayerTwo ] = useState("");

    return <div>
        <input value={playerOne}
               placeholder="Player 1 name"
               onChange={(e) => setPlayerOne(e.target.value)}
        />

        <input value={playerTwo}
               placeholder="Player 2 name"
               onChange={(e) => setPlayerTwo(e.target.value)}
        />

        <ErrorMessage>{message}</ErrorMessage>

        <StartButton onClick={() => onPlayersConfirmed(playerOne, playerTwo)}>
            Play Mancala!
        </StartButton>
    </div>
}