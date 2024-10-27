async function getProvider() {
    if ("solana" in window) {
        const provider = window.solana;
        if (provider.isPhantom) {
            console.log("Is Phantom installed?  ", provider.isPhantom);
            alert("Phantom Wallet is installed");
            return provider;
        }
    } else {
        alert('Please install Phantom Wallet from https://www.phantom.app/');
    }
}

async function connectToWallet() {
    const { solana } = window;
    if (solana) {
        try {
            const response = await solana.connect();
            const publicKey = response.publicKey.toString();
            console.log('Address:', publicKey);
            document.getElementById("key").value = publicKey;
            localStorage.setItem('publicKey', publicKey);
            document.getElementById("connectMessage").style.display = "none";
            document.getElementById("formContainer").style.display = "block";
            return true;
        } catch (error) {
            console.error('Failed to connect to Phantom wallet:', error);
            return false;
        }
    }
    return false;
}

async function transferSOL() {
    const {
        Connection,
        Transaction,
        SystemProgram,
        PublicKey
    } = solanaWeb3;

    if (!window.solana || !window.solana.isPhantom) {
        alert("Please connect your Phantom wallet.");
        return;
    }

    try {
        const provider = window.solana;
        const fromPublicKey = provider.publicKey;
        if (!fromPublicKey) {
            alert("Please connect your Phantom wallet before making a transaction.");
            return;
        }

        const connection = new Connection("https://api.devnet.solana.com", "confirmed");
        const lamportsToSend = document.getElementById('vnd').value;
        const lamports = await convertVND(lamportsToSend);
        const toPublicKey = new PublicKey(document.getElementById('ngNhan').value);

        const transaction = new Transaction().add(
            SystemProgram.transfer({
                fromPubkey: fromPublicKey,
                toPubkey: toPublicKey,
                lamports: lamports,
            })
        );

        transaction.recentBlockhash = (await connection.getRecentBlockhash()).blockhash;
        transaction.feePayer = fromPublicKey;

        const { signature } = await provider.signAndSendTransaction(transaction);
        await connection.confirmTransaction(signature, 'confirmed');

        console.log("Transaction successful! Signature:", signature);

        const balance = await getBalance();
        alert(`Transaction successful: ${lamports} lamports. Remaining balance: ${balance} SOL`);
    } catch (error) {
        if (error.message === "User rejected the request.") {
            alert("You canceled the payment.");
        } else {
            alert("An error occurred.");
            console.error("Error:", error);
        }
    }
}

async function getBalance() {
    const {
        Connection,
        PublicKey,
        LAMPORTS_PER_SOL,
    } = solanaWeb3;

    if (!window.solana || !window.solana.isPhantom) {
        alert("Please connect your Phantom wallet.");
        return;
    }

    try {
        const connection = new Connection("https://api.devnet.solana.com", "confirmed");
        const publicKey = new PublicKey(window.solana.publicKey);
        const balance = await connection.getBalance(publicKey);
        const balanceSOL = balance / LAMPORTS_PER_SOL;
        return balanceSOL;
    } catch (error) {
        console.error("Error:", error);
    }
}

async function convertVND(vndAmount) {
    const response = await fetch('https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=vnd');
    const data = await response.json();
    const exchangeRate = data.solana.vnd;

    const amountSOL = vndAmount / exchangeRate;
    const lamports = Math.floor(amountSOL * Math.pow(10, 9));
    return lamports;
}

document.getElementById("giaoDich").addEventListener("click", transferSOL);
document.getElementById("getProvider").addEventListener("click", getProvider);
document.getElementById("connectToWallet").addEventListener("click", connectToWallet);
document.getElementById("getBalance").addEventListener("click", async function () {
    try {
        const key = document.getElementById('key').value;
        if (!key) {
            alert("Please connect your wallet before checking the balance.");
            return;
        }

        const balance = await getBalance();
        alert(`Your current balance: ${balance} SOL`);
    } catch (error) {
        console.error('Error fetching balance:', error);
    }
});

document.addEventListener("DOMContentLoaded", async function () {
    const publicKey = localStorage.getItem('publicKey');
    if (publicKey) {
        document.getElementById("key").value = publicKey;
        document.getElementById("connectMessage").style.display = "none";
        document.getElementById("formContainer").style.display = "block";
    } else {
        document.getElementById("connectMessage").style.display = "block";
        document.getElementById("formContainer").style.display = "none";
    }
});
