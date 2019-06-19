class ArtistsController < ApplicationController
    before_action :set_artist, only: [:show, :update, :destroy]

  # GET /todos
  def index
    @artists = paginate Artist.unscoped, per_page: 5
  end

  # POST /artists
  def create
    @artist = Artist.create!(artist_params)
    json_response(@artist, :created)
  end

  # GET /artist/:id
  def show
    json_response(@artist)
  end

  # PUT /artists/:id
  def update
    @artist.update(artist_params)
    head :no_content
  end

  # DELETE /artists/:id
  def destroy
    @artist.destroy
    head :no_content
  end

  private

  def artist_params
    # whitelist params
    params.permit(:name)
  end

  def set_artist
    @artist = Artist.find(params[:id])
  end
end
