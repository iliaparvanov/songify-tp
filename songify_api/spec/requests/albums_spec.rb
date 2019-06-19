require 'rails_helper'

RSpec.describe 'Albums API' do
  # Initialize the test data
  let!(:artist) { create(:artist) }
  let!(:albums) { create_list(:album, 20, artist_id: artist.id) }
  let(:artist_id) { artist.id }
  let(:id) { albums.first.id }

  let(:headers) { valid_headers }

  # Test suite for GET /artists/:artist_id/albums
  describe 'GET /artists/:artist_id/albums' do
    before { get "/artists/#{artist_id}/albums", params: {}, headers: headers }

    context 'when artist exists' do
      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end

      it 'returns all artist albums' do
        expect(json.size).to eq(20)
      end
    end

    context 'when artist does not exist' do
      let(:artist_id) { 0 }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end

      it 'returns a not found message' do
        expect(response.body).to match(/Couldn't find Artist/)
      end
    end
  end

  # Test suite for GET /artists/:artist_id/albums/:id
  describe 'GET /artists/:artist_id/albums/:id' do
    before { get "/artists/#{artist_id}/albums/#{id}", params: {}, headers: headers}

    context 'when artist album exists' do
      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end

      it 'returns the album' do
        expect(json['id']).to eq(id)
      end
    end

    context 'when artist album does not exist' do
      let(:id) { 0 }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end

      it 'returns a not found message' do
        expect(response.body).to match(/Couldn't find Album/)
      end
    end
  end

  # Test suite for PUT /artists/:artist_id/albums
  describe 'POST /artists/:artist_id/albums' do
    let(:valid_attributes) { { title: 'vertigo' } }

    context 'when request attributes are valid' do
      before { post "/artists/#{artist_id}/albums", params: valid_attributes, headers: headers}

      it 'returns status code 201' do
        expect(response).to have_http_status(201)
      end
    end

    context 'when an invalid request' do
      before { post "/artists/#{artist_id}/albums", params: {}, headers: headers }

      it 'returns status code 422' do
        expect(response).to have_http_status(422)
      end

      it 'returns a failure message' do
        expect(response.body).to match(/Validation failed: Title can't be blank/)
      end
    end
  end

  # Test suite for PUT /artists/:artist_id/albums/:id
  describe 'PUT /artists/:artist_id/albums/:id' do
    let(:valid_attributes) { { title: 'Igor' } }

    before { put "/artists/#{artist_id}/albums/#{id}", params: valid_attributes, headers: headers }

    context 'when artist exists' do
      it 'returns status code 204' do
        expect(response).to have_http_status(204)
      end

      it 'updates the album' do
        updated_album = Album.find(id)
        expect(updated_album.title).to match(/Igor/)
      end
    end

    context 'when the album does not exist' do
      let(:id) { 0 }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end

      it 'returns a not found message' do
        expect(response.body).to match(/Couldn't find Album/)
      end
    end
  end

  # Test suite for DELETE /artists/:id
  describe 'DELETE /artists/:id' do
    before { delete "/artists/#{artist_id}/albums/#{id}", headers: headers }

    it 'returns status code 204' do
      expect(response).to have_http_status(204)
    end
  end
end