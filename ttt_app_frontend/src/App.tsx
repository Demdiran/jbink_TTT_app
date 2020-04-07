import React, { useState } from "react";
import { Player, PlayerInfo } from "./ttt_app/PlayerInfo"

export function App() {

    const [player, setPlayer] = useState<Player| undefined>(undefined);

    async function getPlayer(){
        console.log("logging in");
        var cookie = getCookie("XSRF-TOKEN");
        const response = await fetch("/user", {
            method: 'GET',
            headers:{
                'Accept' : 'application/json',
                'X-XSRF-TOKEN': cookie
            }
        });
        if(response.status != 401){
            const player: Player = await response.json();
            console.log(player);
            setPlayer(player);
        }
    }

    function getCookie(cname: string){
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
            c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    if(!player){
        getPlayer();
        return (
            <div className="unauthenticated">
                Log in with Google: <a href="/oauth2/authorization/google">click here</a>
            </div>
        );
    }

    return <PlayerInfo player={player}></PlayerInfo>
}