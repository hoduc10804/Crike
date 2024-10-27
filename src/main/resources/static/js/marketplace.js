document.addEventListener('DOMContentLoaded', () => {
  const nftContainer = document.getElementById('nft-container');

  // Function to fetch NFTs from the API
  const fetchNFTs = async () => {
    const apiKey = 'YOUR_API_KEY';
    const endPoint = 'YOUR_API_ENDPOINT';
    const network = 'devnet';
    const marketplaceAddress = 'YOUR_MARKETPLACE_ADDRESS';

    const nftUrl = `${endPoint}marketplace/active_listings?network=${network}&marketplace_address=${marketplaceAddress}`;

    try {
      const response = await fetch(nftUrl, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'x-api-key': apiKey,
        },
      });

      const data = await response.json();

      if (data.success) {
        return data.result;
      } else {
        throw new Error('Failed to fetch NFTs');
      }
    } catch (error) {
      console.error('Error fetching NFTs:', error);
      return [];
    }
  };

  // Function to create NFT card
  const createNFTCard = (nft) => {
    const nftItem = document.createElement('div');
    nftItem.classList.add('nft-item');

    const nftImage = document.createElement('img');
    nftImage.src = nft.image_uri;
    nftImage.alt = nft.name;

    const nftTitle = document.createElement('h2');
    nftTitle.textContent = nft.name;

    const nftPrice = document.createElement('p');
    nftPrice.textContent = `${nft.price} SOL`;

    const buyButton = document.createElement('button');
    buyButton.textContent = 'Buy';
    buyButton.addEventListener('click', () => {
      alert(`Buying ${nft.name} for ${nft.price} SOL`);
      // Implement buy functionality here
    });

    nftItem.appendChild(nftImage);
    nftItem.appendChild(nftTitle);
    nftItem.appendChild(nftPrice);
    nftItem.appendChild(buyButton);

    return nftItem;
  };

  // Function to render NFTs
  const renderNFTs = async () => {
    const nfts = await fetchNFTs();

    nfts.forEach((nft) => {
      const nftCard = createNFTCard(nft);
      nftContainer.appendChild(nftCard);
    });
  };

  // Initial render
  renderNFTs();
});
