import React from "react";
import { GameState, Player } from "./gameState";
import { Board } from "./Board";

interface PlayProps {
    gameState: GameState;
    doMove(player: Player, pitNumber: number): void;
}

export function Play({ gameState, doMove }: PlayProps) {
    return (<div>
        <p>{gameState.players[0].name} vs {gameState.players[1].name}</p>
        <Board gameState={gameState} doMove={doMove}/>
    </div>);
}