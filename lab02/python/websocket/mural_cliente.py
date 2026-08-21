import asyncio
import websockets

# TODO: substitua pelo seu OFFSET pessoal (ver seção 3.3) — use o MESMO valor do servidor
OFFSET = 89

PORTA = 8888 + OFFSET


async def escutar(websocket):
    async for mensagem in websocket:
        print(f"\n{mensagem}")
        print("> ", end="", flush=True)


async def main():
    uri = f"ws://localhost:{PORTA}"
    async with websockets.connect(uri) as websocket:
        print("[WebSocket] Conectado ao mural. Digite 'sair' para encerrar.")
        tarefa_escuta = asyncio.create_task(escutar(websocket))

        loop = asyncio.get_event_loop()
        while True:
            mensagem = await loop.run_in_executor(None, input, "> ")
            if mensagem.lower() == "sair":
                break
            await websocket.send(mensagem)

        tarefa_escuta.cancel()


asyncio.run(main())
