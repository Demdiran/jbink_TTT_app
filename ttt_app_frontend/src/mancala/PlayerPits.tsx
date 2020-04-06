import React from "react";
import { Player, Pit } from "./gameState";

interface PlayerPitProps{
    player: Player;
    doMove(player: Player, pitNumber: number): void;
}
export function PlayerPits( {player, doMove}: PlayerPitProps){
    const pits = player.pits.map((pit: Pit, index: number) => 
        <div key={pit.index} style={{gridColumnStart: index + 1}} onClick={() => doMove(player, pit.index)}>
            {pit.nrOfStones}
        </div>
    );
    return <div className="boardgrid">
        {pits}
    </div>
}