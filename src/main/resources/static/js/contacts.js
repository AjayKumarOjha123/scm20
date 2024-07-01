console.log("Hello Ajay Kumar Ojha this is contact.js");

const baseUrl = "http://localhost:8081";

const viewContactModel = document.getElementById("view_contact_model");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    console.log("modal is shown");
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_model",
  override: true,
};

const contactModal = new Modal(viewContactModel, options, instanceOptions);

function openContactModel() {
  contactModal.show();
}

function closeContactModel() {
  contactModal.hide();
}

async function loadContactData(id) {
  //load contact data call
  try {
    console.log(id);
    const data = await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
    console.log(data);

    document.querySelector("#contact_name").innerHTML = data.name;
    document.querySelector("#contact_email").innerHTML = data.email;

    document.querySelector(
      "#contacts_name"
    ).innerHTML = `<i class="fa-solid fa-user mr-2"></i>${data.name}`;
    document.querySelector(
      "#contacts_email"
    ).innerHTML = `<i class="fa-solid fa-envelope mr-2"></i> ${data.email} `;
    document.querySelector(
      "#contacts_phoneNumber"
    ).innerHTML = `<i class="fa-solid fa-phone mr-2"></i> ${data.phoneNumber}`;
    document.querySelector(
      "#contacts_description"
    ).innerHTML = `<i class="fa-solid fa-circle-info mr-2"></i> ${data.description}`;
    // document.querySelector(
    //   "#contacts_about"
    // ).innerHTML = `<i class="fa-solid fa-address-card mr-2"></i> ${data.about}`;
    document.querySelector(
      "#contacts_address"
    ).innerHTML = `<i class="fa-solid fa-location-dot mr-2"></i> ${data.address}`;

    document.querySelector("#contacts_image").src = data.picture;
    const favoriteContact = document.querySelector("#contacts_favorite");
    if (data.favorite) {
      favoriteContact.innerHTML = `
    <i class='fa-solid fa-heart text-red-500'></i>
    <i class='fa-solid fa-heart text-red-500'></i>
    <i class='fa-solid fa-heart text-red-500'></i>
  `;
    } else {
      favoriteContact.innerHTML = "Not Favorite Contact";
    }
    document.querySelector("#contacts_websites").href = data.websiteLink;
    document.querySelector("#contacts_linkedIn").href = data.linkedInLink;
    openContactModel();
  } catch (error) {
    console.log("Error : " + error);
  }
}

//delete Contact

async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to Delete this contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      const url = `${baseUrl}/user/contacts/delete/` + id;
      deleteConform();
      function windowRedirect() {
        window.location.replace(url);
      }
      setTimeout(() => {
        windowRedirect();
      }, 2000);
    }
  });
}

//successfuly deleted pop-up
async function deleteConform() {
  Swal.fire("Successfuly Deleted!", "", "success");
}
