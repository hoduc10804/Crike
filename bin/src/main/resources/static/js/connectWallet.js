let isConnected = false;
let publicKey = null;
let base64Signature = null;

// Check local storage for connection status, public key, and signature
window.addEventListener('load', () => {
    if (localStorage.getItem('isConnected') === 'true') {
        isConnected = true;
        publicKey = localStorage.getItem('publicKey');
        base64Signature = localStorage.getItem('base64Signature');
        const connectButton = document.getElementById('walletButton');
        connectButton.textContent = 'Disconnect Wallet';
        console.log('Connected with public key:', publicKey);
        console.log('Message signed (Base64):', base64Signature);
        displayForm();
    } else {
        hideForm();
    }
});

const connectWallet = async () => {
    try {
        const response = await window.phantom.solana.connect();
        publicKey = response.publicKey.toString();
        console.log('Connected with public key:', publicKey);

        // Change the button text to "Disconnect Wallet"
        const connectButton = document.getElementById('walletButton');
        connectButton.textContent = 'Disconnect Wallet';
        isConnected = true;

        // Store connection status and public key in local storage
        localStorage.setItem('isConnected', 'true');
        localStorage.setItem('publicKey', publicKey);

        // Sign the message
        const message = 'Hello, Phantom!';
        const encodedMessage = new TextEncoder().encode(message);
        const signedMessage = await window.phantom.solana.signMessage(encodedMessage, 'utf8');
        const signature = signedMessage.signature;

        // Convert the signature to Base64
        base64Signature = btoa(String.fromCharCode(...new Uint8Array(signature)));
        console.log('Message signed (Base64):', base64Signature);

        // Store the signed message and its Base64 signature in local storage
        localStorage.setItem('base64Signature', base64Signature);
        displayForm();
    } catch (error) {
        console.error('Failed to connect and sign message:', error);
    }
};

const disconnectWallet = async () => {
    try {
        console.log('Disconnected from Phantom wallet');
        const connectButton = document.getElementById('walletButton');
        connectButton.textContent = 'Connect Wallet';
        isConnected = false;
        publicKey = null;
        base64Signature = null;

        // Remove connection status, public key, and signature from local storage
        localStorage.removeItem('isConnected');
        localStorage.removeItem('publicKey');
        localStorage.removeItem('base64Signature');
        hideForm();
    } catch (error) {
        console.error('Failed to disconnect from Phantom wallet:', error);
    }
};

const connectAndSignButtonClicked = () => {
    if (isConnected) {
        disconnectWallet();
    } else {
        connectWallet();
    }
};

function displayForm() {
    document.getElementById('connectMessage').style.display = 'none';
    document.querySelectorAll('#formContainer').forEach(el => el.style.display = 'block');
    document.getElementById('wallet').value = publicKey;
}

function hideForm() {
    document.getElementById('connectMessage').style.display = 'block';
    document.querySelectorAll('#formContainer').forEach(el => el.style.display = 'none');
}
