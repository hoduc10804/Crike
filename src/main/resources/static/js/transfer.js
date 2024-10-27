document.addEventListener('DOMContentLoaded', () => {
    const apiKey = 'YOUR_API_KEY';
    const apiUrl = 'YOUR_API_ENDPOINT';
    const walletId = 'YOUR_WALLET_ID';
    
    let transferArr = [];
    let network = document.getElementById('network-select').value;

    const nftGrid = document.getElementById('nft-grid');
    const transferButton = document.getElementById('transfer-button');
    const transferCount = document.getElementById('transfer-count');
    const message = document.getElementById('message');
    const transferPopup = document.getElementById('transfer-popup');
    const recipientAddress = document.getElementById('recipient-address');
    const errorMessage = document.getElementById('error-message');

    // Fetch NFTs
    async function fetchNFTs() {
        const response = await fetch(`${apiUrl}nft/read_all?network=${network}&address=${walletId}`, {
            headers: {
                'Content-Type': 'application/json',
                'x-api-key': apiKey
            }
        });
        const data = await response.json();
        displayNFTs(data.result);
    }

    // Display NFTs
    function displayNFTs(nfts) {
        nftGrid.innerHTML = '';
        nfts.forEach(nft => {
            const nftCard = document.createElement('div');
            nftCard.classList.add('nft-card');
            nftCard.innerHTML = `
                <img src="${nft.cached_image_uri}" alt="${nft.name}">
                <p>${nft.name.length > 8 ? nft.name.substring(0, 8) + '...' : nft.name}</p>
                <button onclick="toggleSelection('${nft.mint}')">Select</button>
            `;
            nftGrid.appendChild(nftCard);
        });
    }

    // Toggle NFT selection
    window.toggleSelection = (mint) => {
        const index = transferArr.indexOf(mint);
        if (index > -1) {
            transferArr.splice(index, 1);
        } else {
            transferArr.push(mint);
        }
        updateTransferButton();
    };

    // Update transfer button
    function updateTransferButton() {
        transferCount.textContent = transferArr.length;
        transferButton.disabled = transferArr.length === 0;
    }

    // Open transfer popup
    transferButton.addEventListener('click', () => {
        transferPopup.classList.remove('hidden');
    });

    // Close transfer popup
    document.getElementById('popup-close').addEventListener('click', () => {
        transferPopup.classList.add('hidden');
        recipientAddress.value = '';
        errorMessage.textContent = '';
    });

    // Confirm transfer
    document.getElementById('confirm-transfer').addEventListener('click', async () => {
        const toAddress = recipientAddress.value;
        if (!toAddress) {
            errorMessage.textContent = 'Wallet Address cannot be empty';
            return;
        }
        transferPopup.classList.add('hidden');
        message.textContent = 'Transferring NFTs...';
        try {
            const response = await fetch(`${apiUrl}nft/transfer_many`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'x-api-key': apiKey
                },
                body: JSON.stringify({
                    network: network,
                    token_addresses: transferArr,
                    from_address: walletId,
                    to_address: toAddress
                })
            });
            const data = await response.json();
            if (data.success) {
                message.textContent = 'Transfer successful';
                fetchNFTs();
            } else {
                message.textContent = 'Transfer failed';
            }
        } catch (error) {
            message.textContent = 'Error occurred: ' + error.message;
        }
        transferArr = [];
        updateTransferButton();
    });

    // Fetch NFTs on network change
    document.getElementById('network-select').addEventListener('change', (e) => {
        network = e.target.value;
        fetchNFTs();
    });

    // Initial fetch
    fetchNFTs();
});
