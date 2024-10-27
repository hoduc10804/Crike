let publicKey = ""; // Placeholder for public key variable
let isConnected = false; // Placeholder for connection status

// Check local storage for connection status and public key
function checkConnection() {
    if (localStorage.getItem('isConnected') === 'true') {
        isConnected = true;
        publicKey = localStorage.getItem('publicKey');
        displayForm();
    } else {
        hideForm();
    }
}

function displayForm() {
    document.getElementById('connectMessage').style.display = 'none';
    document.querySelectorAll('#formContainer').forEach(el => el.style.display = 'block');
    document.getElementById('wallet').value = publicKey;
}

function hideForm() {
    document.getElementById('connectMessage').style.display = 'block';
    document.querySelectorAll('#formContainer').forEach(el => el.style.display = 'none');
}

document.getElementById('file').addEventListener('change', function (e) {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.onload = function (event) {
        document.getElementById('displayPic').src = event.target.result;
    };
    reader.readAsDataURL(file);
});

document.getElementById('createForm').addEventListener('submit', function (e) {
    e.preventDefault();
    document.getElementById('status').textContent = "Loading";
    const formData = new FormData();
    formData.append("network", document.getElementById('network').value);
    formData.append("wallet", publicKey); // Set this value accordingly
    formData.append("name", document.getElementById('name').value);
    formData.append("symbol", document.getElementById('symbol').value);
    formData.append("description", document.getElementById('desc').value);
    formData.append("attributes", JSON.stringify([{ "trait_type": "edification", "value": "100" }])); // Adjust attribute handling as needed
    formData.append("external_url", document.getElementById('externalUrl').value);
    formData.append("max_supply", document.getElementById('maxSupply').value);
    formData.append("royalty", document.getElementById('royalty').value);
    formData.append("file", document.getElementById('file').files[0]);

    axios({
        url: "https://api.shyft.to/sol/v1/nft/create_detach",
        method: "POST",
        headers: {
            "Content-Type": "multipart/form-data",
            "x-api-key": xApiKey,
            Accept: "*/*",
            "Access-Control-Allow-Origin": "*",
        },
        data: formData,
    })
    .then(async (res) => {
        console.log(res);
        if (res.data.success === true) {
            document.getElementById('status').textContent = "success: Transaction Created. Signing Transactions. Please Wait.";
            const transaction = res.data.result.encoded_transaction;
            const ret_result = await signAndConfirmTransactionFe(network, transaction, callback);
            console.log(ret_result);
        }
    })
    .catch((err) => {
        console.warn(err);
        document.getElementById('status').textContent = "success: false";
    });
});

function updateRemoveButtons() {
    var attributeRows = document.querySelectorAll('.attribute-row');
}

document.getElementById('addAttributeBtn').addEventListener('click', function () {
    var container = document.getElementById('attributesContainer');
    var attributeRow = document.createElement('div');
    attributeRow.className = 'row mb-2 attribute-row';
    attributeRow.innerHTML = `
        <div class="col">
            <input type="text" class="form-control" placeholder="Trait Type">
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="Value">
        </div>
        <div class="col-auto remove-attribute-container">
            <button type="button" class="btn btn-danger btn-sm remove-attribute-btn">🗑</button>
        </div>
    `;
    container.appendChild(attributeRow);
    updateRemoveButtons();
});

document.addEventListener('click', function (e) {
    if (e.target && e.target.classList.contains('remove-attribute-btn')) {
        e.target.closest('.attribute-row').remove();
        updateRemoveButtons();
    }
});

// Initial update on page load
updateRemoveButtons();

// Check connection status on page load
document.addEventListener('DOMContentLoaded', checkConnection);
