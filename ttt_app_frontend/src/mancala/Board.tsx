import React from "react";
import { GameState, Player } from "./gameState";
import { PlayerPits } from "./PlayerPits";

interface BoardProps{
    gameState: GameState;
    doMove(player: Player, pitNumber: number): void;
}

export function Board( { gameState, doMove } : BoardProps){
    return (
        <div>
            <PlayerPits doMove={doMove} player={gameState.players[0]}/>
            <PlayerPits doMove={doMove} player={gameState.players[1]}/>
        </div>);
}